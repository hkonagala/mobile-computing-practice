package com.example.harikakonagala.practice;

/**
 * Created by Harika Konagala on 3/24/2017.
 */
public class AccelerometerData {
    Float accx,accy,accz;

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
        this.accx =accx;
        this.accy =accy;
        this.accz =accz;

    }

    public Float getRms(){
        double accxs=Math.pow(getAccx().doubleValue(),2);
        double accys=Math.pow(getAccy().doubleValue(),2);
        double acczs=Math.pow(getAccz().doubleValue(),2);
        return new Float(Math.sqrt(accxs+accys+acczs));
    }
}
