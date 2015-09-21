package location.restaurant.mit.uoc.lk.locationapp;

import java.io.Serializable;

/**
 * Created by nilan on 9/13/15.
 */
public class GpsLocation implements Serializable{
        private static final long serialVersionUID = -7788619177798333712L;

        private String latitude;
        private String longitude;
        private String mac;
        private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }



}
