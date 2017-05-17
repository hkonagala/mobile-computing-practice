package com.example.android.inclassassignment;

/**
 * Created by phani on 2/28/17.
 */

public class AccelerometerData {
    Float accx;
    Float accy;
    Float accz;

    public Float getAccx() {
        return accx;
    }

    public void setAccx(Float accx) {
        this.accx = accx;
    }

    public Float getAccy() {
        return accy;
    }

    public void setAccy(Float accy) {
        this.accy = accy;
    }

    public Float getAccz() {
        return accz;
    }

    public void setAccz(Float accz) {
        this.accz = accz;
    }

    public AccelerometerData(Float accx, Float accy, Float accz){
        this.accx = accx;
        this.accy = accy;
        this.accz = accz;
    }

    public Float getRMSItem(){
        double accXS = Math.pow(getAccx().doubleValue(), 2);
        double accYS = Math.pow(getAccy().doubleValue(), 2);
        double accZS = Math.pow(getAccz().doubleValue(), 2);
        return new Float(Math.sqrt(accXS+accYS+accZS));
    }

}
