/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiwang.photoalbumlibrary.matisse.xiaohongshu;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.weiwang.photoalbumlibrary.R;
import com.weiwang.photoalbumlibrary.matisse.internal.entity.Album;
import com.weiwang.photoalbumlibrary.matisse.internal.entity.Item;
import com.weiwang.photoalbumlibrary.matisse.internal.entity.SelectionSpec;
import com.weiwang.photoalbumlibrary.matisse.internal.model.AlbumMediaCollection;
import com.weiwang.photoalbumlibrary.matisse.internal.model.SelectedItemCollection;
import com.weiwang.photoalbumlibrary.matisse.internal.ui.adapter.AlbumMediaAdapter;
import com.weiwang.photoalbumlibrary.matisse.internal.ui.widget.MediaGridInset;
import com.weiwang.photoalbumlibrary.matisse.internal.utils.PathUtils;
import com.weiwang.photoalbumlibrary.matisse.internal.utils.UIUtils;

import java.io.File;
import java.nio.file.Path;

public class XiaohongshuFragment extends Fragment implements
        AlbumMediaCollection.AlbumMediaCallbacks, AlbumMediaAdapter.CheckStateListener,
        AlbumMediaAdapter.OnMediaClickListener {

    public static final String EXTRA_ALBUM = "extra_album";

    private final AlbumMediaCollection mAlbumMediaCollection = new AlbumMediaCollection();
    private AlbumMediaAdapter mAdapter;
    private SelectionProvider mSelectionProvider;
    private AlbumMediaAdapter.CheckStateListener mCheckStateListener;
    private AlbumMediaAdapter.OnMediaClickListener mOnMediaClickListener;


    private Toolbar mToolbar;
    MCropImageView mMCropImageView;
    CoordinatorRecyclerView mRecyclerView;
    CoordinatorLinearLayout mCoordinatorLayout;

    public static XiaohongshuFragment newInstance(Album album) {
        XiaohongshuFragment fragment = new XiaohongshuFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_ALBUM, album);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("test" ,"~~~~~~~~" + context);
        if (context instanceof SelectionProvider) {
            mSelectionProvider = (SelectionProvider) context;
        } else {
            throw new IllegalStateException("Context must implement SelectionProvider.");
        }
        if (context instanceof AlbumMediaAdapter.CheckStateListener) {
            mCheckStateListener = (AlbumMediaAdapter.CheckStateListener) context;
        }
        if (context instanceof AlbumMediaAdapter.OnMediaClickListener) {
            mOnMediaClickListener = (AlbumMediaAdapter.OnMediaClickListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mei_crop_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mMCropImageView = view.findViewById(R.id.crop_view);
        mRecyclerView = view.findViewById(R.id.recycler);
        mCoordinatorLayout = view.findViewById(R.id.coordinator_layout);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        mRecyclerView.setAdapter(mAdapter);


        // 实现回调接口
        mRecyclerView.setOnCoordinatorListener(new CoordinatorRecyclerView.OnCoordinatorListener() {
            @Override
            public void onScroll(float y, float deltaY, int maxParentScrollRange) {
                mCoordinatorLayout.onScroll(y, deltaY, maxParentScrollRange);
            }

            @Override
            public void onFiling(int velocityY) {
                mCoordinatorLayout.onFiling(velocityY);
            }

            @Override
            public void handlerInvalidClick(int rawX, int rawY) {
                handlerRecyclerInvalidClick(mRecyclerView, rawX, rawY);
            }
        });

        mCoordinatorLayout.setOnScrollListener(new CoordinatorLinearLayout.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                mRecyclerView.setCurrentParenScrollY(scrollY);
            }

            @Override
            public void isExpand(boolean isExpand) {
                mRecyclerView.setExpand(isExpand);
            }

            @Override
            public void completeExpand() {
                mRecyclerView.resetRecyclerHeight();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Album album = getArguments().getParcelable(EXTRA_ALBUM);

        mAdapter = new AlbumMediaAdapter(getContext(),
                mSelectionProvider.provideSelectedItemCollection(), mRecyclerView);
        mAdapter.registerCheckStateListener(this);
        mAdapter.registerOnMediaClickListener(this);
        mRecyclerView.setHasFixedSize(true);

        int spanCount;
        SelectionSpec selectionSpec = SelectionSpec.getInstance();
        if (selectionSpec.gridExpectedSize > 0) {
            spanCount = UIUtils.spanCount(getContext(), selectionSpec.gridExpectedSize);
        } else {
            spanCount = selectionSpec.spanCount;
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));

        int spacing = getResources().getDimensionPixelSize(R.dimen.media_grid_spacing);
        mRecyclerView.addItemDecoration(new MediaGridInset(spanCount, spacing, false));
        mRecyclerView.setAdapter(mAdapter);
        mAlbumMediaCollection.onCreate(getActivity(), this);
        mAlbumMediaCollection.load(album, selectionSpec.capture);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAlbumMediaCollection.onDestroy();
    }

    public void refreshMediaGrid() {
        mAdapter.notifyDataSetChanged();
    }

    public void refreshSelection() {
        mAdapter.refreshSelection();
    }

    @Override
    public void onAlbumMediaLoad(Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onAlbumMediaReset() {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onUpdate() {
        // notify outer Activity that check state changed
        if (mCheckStateListener != null) {
            mCheckStateListener.onUpdate();
        }
    }

    @Override
    public void onMediaClick(Album album, Item item, int adapterPosition) {
        if(item.isImage()){
            mMCropImageView.setImagePath(PathUtils.getPath(getContext(), item.getContentUri()));
        }else{
            mOnMediaClickListener.onMediaClick((Album) getArguments().getParcelable(EXTRA_ALBUM),
                    item, adapterPosition);
        }
    }

    @Override
    public void onCheakClick(Album album, Item item, int adapterPosition, boolean cheack) {
        if(!cheack){
            return;
        }
        if(item.isImage()){
            mMCropImageView.setImagePath(PathUtils.getPath(getContext(), item.getContentUri()));
        }else{
            mOnMediaClickListener.onMediaClick((Album) getArguments().getParcelable(EXTRA_ALBUM),
                    item, adapterPosition);
        }
    }

    public interface SelectionProvider {
        SelectedItemCollection provideSelectedItemCollection();
    }

    /**
     * @param recyclerView
     * @param touchX
     * @param touchY
     */
    public void handlerRecyclerInvalidClick(RecyclerView recyclerView, int touchX, int touchY) {
        if (recyclerView != null && recyclerView.getChildCount() > 0) {
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                View childView = recyclerView.getChildAt(i);
                if (childView != null) {
                    if (childView != null && isTouchView(touchX, touchY, childView)) {
                        childView.performClick();
                        return;
                    }
                }
            }
        }
    }

    // 触摸点是否view区域内 parent.isPointInChildBounds(child, x, y)
    private boolean isTouchView(int touchX, int touchY, View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        return rect.contains(touchX, touchY);
    }
}
