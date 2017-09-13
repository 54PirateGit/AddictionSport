package com.tianbao.addictionsport.mode;

import java.util.List;

/**
 * 课程详情
 * Created by edianzu on 2017/9/12.
 */

public class CourseDetail {


    /**
     * code : 200
     * data : {"address":{"detailAddress":"湖南长沙市岳麓区麓山南路店299号渔湾码头时尚商业广场1层","latitude":"28.1692535039","longitude":"112.9443454742","name":"天马冲瘾店"},"button":{"disable":true,"title":"立即预约"},"care":"1.本店提供更衣室、储物柜、卫生间，不设淋浴；\n2.课程开始前6小时后取消预约，不支持退款；\n3.室内设高速Wifi，密码：xxxxx；","coach":{"avatar":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg","desc":"","id":1,"nick":"棋子"},"crowd":"适合所有新同学，以及想要平均增进力量与柔软度的练习者。","description":"课程结合了强化力量和放松延展这两种元素，让身体更加平衡。练习者会在课程中体验振奋、愉悦的体位法，也会长时间停留在部分深层的延展动作中，培养冥想的平静与专注。","faq":"1.问题1：xxx\n2.问题2：xxx\n3.问题3：xxx","id":86,"price":"¥0.2","shortTime":"18.00-18.50","stock":30,"tags":["瑜珈","拉升"],"time":"2017-09-06 18.00-18.50","title":"Yin-Yang瑜伽1","trainingEffect":"效果\n效果2\n效果3","yenPrice":"¥0.17"}
     * message : SUCCESS
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * address : {"detailAddress":"湖南长沙市岳麓区麓山南路店299号渔湾码头时尚商业广场1层","latitude":"28.1692535039","longitude":"112.9443454742","name":"天马冲瘾店"}
         * button : {"disable":true,"title":"立即预约"}
         * care : 1.本店提供更衣室、储物柜、卫生间，不设淋浴；
         2.课程开始前6小时后取消预约，不支持退款；
         3.室内设高速Wifi，密码：xxxxx；
         * coach : {"avatar":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg","desc":"","id":1,"nick":"棋子"}
         * crowd : 适合所有新同学，以及想要平均增进力量与柔软度的练习者。
         * description : 课程结合了强化力量和放松延展这两种元素，让身体更加平衡。练习者会在课程中体验振奋、愉悦的体位法，也会长时间停留在部分深层的延展动作中，培养冥想的平静与专注。
         * faq : 1.问题1：xxx
         2.问题2：xxx
         3.问题3：xxx
         * id : 86
         * price : ¥0.2
         * shortTime : 18.00-18.50
         * stock : 30
         * tags : ["瑜珈","拉升"]
         * time : 2017-09-06 18.00-18.50
         * title : Yin-Yang瑜伽1
         * trainingEffect : 效果
         效果2
         效果3
         * yenPrice : ¥0.17
         */

        private AddressBean address;
        private ButtonBean button;
        private String care;
        private CoachBean coach;
        private String crowd;
        private String description;
        private String faq;
        private int id;
        private String price;
        private String shortTime;
        private int stock;
        private String time;
        private String title;
        private String trainingEffect;
        private String yenPrice;
        private List<String> tags;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public ButtonBean getButton() {
            return button;
        }

        public void setButton(ButtonBean button) {
            this.button = button;
        }

        public String getCare() {
            return care;
        }

        public void setCare(String care) {
            this.care = care;
        }

        public CoachBean getCoach() {
            return coach;
        }

        public void setCoach(CoachBean coach) {
            this.coach = coach;
        }

        public String getCrowd() {
            return crowd;
        }

        public void setCrowd(String crowd) {
            this.crowd = crowd;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFaq() {
            return faq;
        }

        public void setFaq(String faq) {
            this.faq = faq;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getShortTime() {
            return shortTime;
        }

        public void setShortTime(String shortTime) {
            this.shortTime = shortTime;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTrainingEffect() {
            return trainingEffect;
        }

        public void setTrainingEffect(String trainingEffect) {
            this.trainingEffect = trainingEffect;
        }

        public String getYenPrice() {
            return yenPrice;
        }

        public void setYenPrice(String yenPrice) {
            this.yenPrice = yenPrice;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        // 地址
        public static class AddressBean {
            /**
             * detailAddress : 湖南长沙市岳麓区麓山南路店299号渔湾码头时尚商业广场1层
             * latitude : 28.1692535039
             * longitude : 112.9443454742
             * name : 天马冲瘾店
             */

            private String detailAddress;
            private String latitude;
            private String longitude;
            private String name;

            public String getDetailAddress() {
                return detailAddress;
            }

            public void setDetailAddress(String detailAddress) {
                this.detailAddress = detailAddress;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        // 预约
        public static class ButtonBean {
            /**
             * disable : true
             * title : 立即预约
             */

            private boolean disable;
            private String title;

            public boolean isDisable() {
                return disable;
            }

            public void setDisable(boolean disable) {
                this.disable = disable;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        // 教练
        public static class CoachBean {
            /**
             * avatar : https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg
             * desc :
             * id : 1
             * nick : 棋子
             */

            private String avatar;
            private String desc;
            private int id;
            private String nick;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }
        }
    }
}
