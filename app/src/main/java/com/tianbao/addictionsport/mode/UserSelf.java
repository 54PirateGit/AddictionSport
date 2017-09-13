package com.tianbao.addictionsport.mode;

import java.io.Serializable;
import java.util.List;

/**
 * 个人中心
 * Created by edianzu on 2017/9/13.
 */

public class UserSelf implements Serializable {

    /**
     * code : 200
     * data : {"beyond":"您已经超过 3 %同时健身的同学","calorieTotal":"0","cards":[{"button":{"disable":true,"title":"充值"},"cardId":18,"cash":"550","discount":"消费立打8.5折","gift":"126.2","total":"676.2","type":1},{"button":{"disable":true,"title":"充值"},"cardId":19,"cash":"100","discount":"消费立打8.5折","gift":"50","total":"150","type":2}],"durationTotal":{"fractionalPart":"1/4","intPart":"10","origin":"10.25"},"durationWeek":{"fractionalPart":"2/4","intPart":"11","origin":"11.5"},"orders":[{"button":{"disable":true,"title":"取消预约"},"course":{"address":{"detailAddress":"湖南长沙市岳麓区麓山南路店299号渔湾码头时尚商业广场1层","latitude":"28.1692535039","longitude":"112.9443454742","name":"天马冲瘾店"},"button":{"disable":true,"title":"立即预约"},"coach":{"avatar":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg","id":1},"id":89,"price":"¥20","shortTime":"18.00-18.50","stock":30,"tags":["瑜珈","拉升"],"time":"2017-09-05 18.00-18.50","title":"Yin-Yang瑜伽1","yenPrice":"¥17"},"order":{"createTime":"2017-09-03 23:07:22","id":48,"orderId":"20170903230732327002","status":"已预约"},"personTime":[{"num":2,"select":true,"title":"【2人】"}],"realPay":{"fee":"¥0","originFee":0,"title":"实付款"}}],"point":0,"userId":18}
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

    public static class DataBean implements Serializable {
        /**
         * beyond : 您已经超过 3 %同时健身的同学
         * calorieTotal : 0
         * cards : [{"button":{"disable":true,"title":"充值"},"cardId":18,"cash":"550","discount":"消费立打8.5折","gift":"126.2","total":"676.2","type":1},{"button":{"disable":true,"title":"充值"},"cardId":19,"cash":"100","discount":"消费立打8.5折","gift":"50","total":"150","type":2}]
         * durationTotal : {"fractionalPart":"1/4","intPart":"10","origin":"10.25"}
         * durationWeek : {"fractionalPart":"2/4","intPart":"11","origin":"11.5"}
         * orders : [{"button":{"disable":true,"title":"取消预约"},"course":{"address":{"detailAddress":"湖南长沙市岳麓区麓山南路店299号渔湾码头时尚商业广场1层","latitude":"28.1692535039","longitude":"112.9443454742","name":"天马冲瘾店"},"button":{"disable":true,"title":"立即预约"},"coach":{"avatar":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg","id":1},"id":89,"price":"¥20","shortTime":"18.00-18.50","stock":30,"tags":["瑜珈","拉升"],"time":"2017-09-05 18.00-18.50","title":"Yin-Yang瑜伽1","yenPrice":"¥17"},"order":{"createTime":"2017-09-03 23:07:22","id":48,"orderId":"20170903230732327002","status":"已预约"},"personTime":[{"num":2,"select":true,"title":"【2人】"}],"realPay":{"fee":"¥0","originFee":0,"title":"实付款"}}]
         * point : 0
         * userId : 18
         */

        private String beyond;
        private String calorieTotal;
        private DurationTotalBean durationTotal;
        private DurationWeekBean durationWeek;
        private int point;
        private int userId;
        private List<CardsBean> cards;
        private List<OrdersBean> orders;

        public String getBeyond() {
            return beyond;
        }

        public void setBeyond(String beyond) {
            this.beyond = beyond;
        }

        public String getCalorieTotal() {
            return calorieTotal;
        }

        public void setCalorieTotal(String calorieTotal) {
            this.calorieTotal = calorieTotal;
        }

        public DurationTotalBean getDurationTotal() {
            return durationTotal;
        }

        public void setDurationTotal(DurationTotalBean durationTotal) {
            this.durationTotal = durationTotal;
        }

        public DurationWeekBean getDurationWeek() {
            return durationWeek;
        }

        public void setDurationWeek(DurationWeekBean durationWeek) {
            this.durationWeek = durationWeek;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<CardsBean> getCards() {
            return cards;
        }

        public void setCards(List<CardsBean> cards) {
            this.cards = cards;
        }

        public List<OrdersBean> getOrders() {
            return orders;
        }

        public void setOrders(List<OrdersBean> orders) {
            this.orders = orders;
        }

        public static class DurationTotalBean implements Serializable {
            /**
             * fractionalPart : 1/4
             * intPart : 10
             * origin : 10.25
             */

            private String fractionalPart;
            private String intPart;
            private String origin;

            public String getFractionalPart() {
                return fractionalPart;
            }

            public void setFractionalPart(String fractionalPart) {
                this.fractionalPart = fractionalPart;
            }

            public String getIntPart() {
                return intPart;
            }

            public void setIntPart(String intPart) {
                this.intPart = intPart;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }
        }

        public static class DurationWeekBean implements Serializable {
            /**
             * fractionalPart : 2/4
             * intPart : 11
             * origin : 11.5
             */

            private String fractionalPart;
            private String intPart;
            private String origin;

            public String getFractionalPart() {
                return fractionalPart;
            }

            public void setFractionalPart(String fractionalPart) {
                this.fractionalPart = fractionalPart;
            }

            public String getIntPart() {
                return intPart;
            }

            public void setIntPart(String intPart) {
                this.intPart = intPart;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }
        }

        public static class CardsBean implements Serializable {
            /**
             * button : {"disable":true,"title":"充值"}
             * cardId : 18
             * cash : 550
             * discount : 消费立打8.5折
             * gift : 126.2
             * total : 676.2
             * type : 1
             */

            private ButtonBean button;
            private int cardId;
            private String cash;
            private String discount;
            private String gift;
            private String total;
            private int type;

            public ButtonBean getButton() {
                return button;
            }

            public void setButton(ButtonBean button) {
                this.button = button;
            }

            public int getCardId() {
                return cardId;
            }

            public void setCardId(int cardId) {
                this.cardId = cardId;
            }

            public String getCash() {
                return cash;
            }

            public void setCash(String cash) {
                this.cash = cash;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getGift() {
                return gift;
            }

            public void setGift(String gift) {
                this.gift = gift;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public static class ButtonBean implements Serializable {
                /**
                 * disable : true
                 * title : 充值
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
        }

        public static class OrdersBean implements Serializable {
            /**
             * button : {"disable":true,"title":"取消预约"}
             * course : {"address":{"detailAddress":"湖南长沙市岳麓区麓山南路店299号渔湾码头时尚商业广场1层","latitude":"28.1692535039","longitude":"112.9443454742","name":"天马冲瘾店"},"button":{"disable":true,"title":"立即预约"},"coach":{"avatar":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg","id":1},"id":89,"price":"¥20","shortTime":"18.00-18.50","stock":30,"tags":["瑜珈","拉升"],"time":"2017-09-05 18.00-18.50","title":"Yin-Yang瑜伽1","yenPrice":"¥17"}
             * order : {"createTime":"2017-09-03 23:07:22","id":48,"orderId":"20170903230732327002","status":"已预约"}
             * personTime : [{"num":2,"select":true,"title":"【2人】"}]
             * realPay : {"fee":"¥0","originFee":0,"title":"实付款"}
             */

            private ButtonBeanX button;
            private CourseBean course;
            private OrderBean order;
            private RealPayBean realPay;
            private List<PersonTimeBean> personTime;

            public ButtonBeanX getButton() {
                return button;
            }

            public void setButton(ButtonBeanX button) {
                this.button = button;
            }

            public CourseBean getCourse() {
                return course;
            }

            public void setCourse(CourseBean course) {
                this.course = course;
            }

            public OrderBean getOrder() {
                return order;
            }

            public void setOrder(OrderBean order) {
                this.order = order;
            }

            public RealPayBean getRealPay() {
                return realPay;
            }

            public void setRealPay(RealPayBean realPay) {
                this.realPay = realPay;
            }

            public List<PersonTimeBean> getPersonTime() {
                return personTime;
            }

            public void setPersonTime(List<PersonTimeBean> personTime) {
                this.personTime = personTime;
            }

            public static class ButtonBeanX implements Serializable {
                /**
                 * disable : true
                 * title : 取消预约
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

            public static class CourseBean implements Serializable {
                /**
                 * address : {"detailAddress":"湖南长沙市岳麓区麓山南路店299号渔湾码头时尚商业广场1层","latitude":"28.1692535039","longitude":"112.9443454742","name":"天马冲瘾店"}
                 * button : {"disable":true,"title":"立即预约"}
                 * coach : {"avatar":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg","id":1}
                 * id : 89
                 * price : ¥20
                 * shortTime : 18.00-18.50
                 * stock : 30
                 * tags : ["瑜珈","拉升"]
                 * time : 2017-09-05 18.00-18.50
                 * title : Yin-Yang瑜伽1
                 * yenPrice : ¥17
                 */

                private AddressBean address;
                private ButtonBeanXX button;
                private CoachBean coach;
                private int id;
                private String price;
                private String shortTime;
                private int stock;
                private String time;
                private String title;
                private String yenPrice;
                private List<String> tags;

                public AddressBean getAddress() {
                    return address;
                }

                public void setAddress(AddressBean address) {
                    this.address = address;
                }

                public ButtonBeanXX getButton() {
                    return button;
                }

                public void setButton(ButtonBeanXX button) {
                    this.button = button;
                }

                public CoachBean getCoach() {
                    return coach;
                }

                public void setCoach(CoachBean coach) {
                    this.coach = coach;
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

                public static class AddressBean implements Serializable {
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

                public static class ButtonBeanXX implements Serializable {
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

                public static class CoachBean implements Serializable {
                    /**
                     * avatar : https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg
                     * id : 1
                     */

                    private String avatar;
                    private int id;

                    public String getAvatar() {
                        return avatar;
                    }

                    public void setAvatar(String avatar) {
                        this.avatar = avatar;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }
                }
            }

            public static class OrderBean implements Serializable {
                /**
                 * createTime : 2017-09-03 23:07:22
                 * id : 48
                 * orderId : 20170903230732327002
                 * status : 已预约
                 */

                private String createTime;
                private int id;
                private String orderId;
                private String status;

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getOrderId() {
                    return orderId;
                }

                public void setOrderId(String orderId) {
                    this.orderId = orderId;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }
            }

            public static class RealPayBean implements Serializable {
                /**
                 * fee : ¥0
                 * originFee : 0
                 * title : 实付款
                 */

                private String fee;
                private int originFee;
                private String title;

                public String getFee() {
                    return fee;
                }

                public void setFee(String fee) {
                    this.fee = fee;
                }

                public int getOriginFee() {
                    return originFee;
                }

                public void setOriginFee(int originFee) {
                    this.originFee = originFee;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }

            public static class PersonTimeBean implements Serializable {
                /**
                 * num : 2
                 * select : true
                 * title : 【2人】
                 */

                private int num;
                private boolean select;
                private String title;

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public boolean isSelect() {
                    return select;
                }

                public void setSelect(boolean select) {
                    this.select = select;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }
    }
}
