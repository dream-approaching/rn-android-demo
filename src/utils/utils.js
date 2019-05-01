// debounce 防抖
export const debounce = (fn, ms = 300) => {
  let timeoutId;
  return function(...args) {
    if (timeoutId) clearTimeout(timeoutId);
    timeoutId = setTimeout(() => fn.apply(this, args), ms);
  };
};

// throttle 节流
export const throttle = (fn, wait) => {
  let inThrottle;
  let lastFn;
  let lastTime;
  return function(...args) {
    const context = this;
    if (!inThrottle) {
      fn.apply(context, args);
      lastTime = Date.now();
      inThrottle = true;
    } else {
      clearTimeout(lastFn);
      lastFn = setTimeout(function() {
        if (Date.now() - lastTime >= wait) {
          fn.apply(context, args);
          lastTime = Date.now();
        }
      }, Math.max(wait - (Date.now() - lastTime), 0));
    }
  };
};

export const lastArr = arr => arr[arr.length - 1];

export const spiltHighlightText = (text, highlightKey) => {
  const highlightIndex = text.indexOf(highlightKey);
  if (highlightIndex < 0) return [text];
  const arr = [
    text.slice(0, highlightIndex),
    text.slice(highlightIndex, highlightIndex + highlightKey.length),
    text.slice(highlightIndex + highlightKey.length),
  ];
  return arr;
};
