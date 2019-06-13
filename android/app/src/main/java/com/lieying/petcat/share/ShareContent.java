package com.lieying.petcat.share;

/**
 * 分享内容类
 */
public class ShareContent {

    private String title;
    private String content;
    private String imgUrl;
    private String url; // 分享的链接
    private QrShareContent qrShareContent;

    public String getTitle() {
        return title;
    }

    public ShareContent setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ShareContent setContent(String content) {
        this.content = content;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public ShareContent setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }


    public String getUrl() {
        return url;
    }

    public ShareContent setUrl(String url) {
        this.url = url;
        return this;
    }


    public QrShareContent getQrShareContent() {
        return qrShareContent;
    }

    public void setQrShareContent(QrShareContent qrShareContent) {
        this.qrShareContent = qrShareContent;
    }

    public static class QrShareContent {
        private String goodsPic;
        private String slogan;
        private String qrCodeUrl;
        private String price;
        private String originPrice;
        private String storeName;
        private String countryImageUrl;

        public String getGoodsPic() {
            return goodsPic;
        }

        public String getSlogan() {
            return slogan;
        }

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        public String getPrice() {
            return price;
        }

        public String getOriginPrice() {
            return originPrice;
        }

        public String getStoreName() {
            return storeName;
        }

        public String getCountryImageUrl() {
            return countryImageUrl;
        }

        public QrShareContent setGoodsPic(String goodsPic) {
            this.goodsPic = goodsPic;
            return this;
        }

        public QrShareContent setSlogan(String slogan) {
            this.slogan = slogan;
            return this;
        }

        public QrShareContent setQrCodeUrl(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
            return this;
        }

        public QrShareContent setPrice(String price) {
            this.price = price;
            return this;
        }

        public QrShareContent setOriginPrice(String originPrice) {
            this.originPrice = originPrice;
            return this;
        }

        public QrShareContent setStoreName(String storeName) {
            this.storeName = storeName;
            return this;
        }

        public QrShareContent setCountryImageUrl(String countryImageUrl) {
            this.countryImageUrl = countryImageUrl;
            return this;
        }
    }
}
