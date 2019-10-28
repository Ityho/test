package com.miduchina.wrd.bo.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: VipInfoBO
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 3:08 PM
 */
@Data
@NoArgsConstructor
public class VipInfoBO {
    private double minAmount;	// 最低充值金额
    private double maxAmount;	// 最高充值金额
    private double creditRechargeAmount;	// 微积分累计充值金额
    private int level;	//vip等级
    private double discount;	//折扣
    private VipInfoBO nextVipInfo;	//下一级vip权益
    private List<VipInfoBO> vipInfos;	//vip对应权益表

    public VipInfoBO(double minAmount, double maxAmount, int level, double discount) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.level = level;
        this.discount = discount;
    }

    /**
     * 获取vip信息
     * @param amount
     */
    public VipInfoBO(Double amount){
        if(amount == null) {
            amount = 0d;
        }
        if(vipInfos != null && vipInfos.size() > 0)
            this.initVipInfos();
        this.creditRechargeAmount = amount;
        this.level = 0;
        this.discount = 1;
        for(int i=0; i<this.vipInfos.size(); i++){
            if(this.vipInfos.get(i).getMaxAmount() == 0){	//V5
                if(amount >= this.vipInfos.get(i).getMinAmount()){
                    this.level = this.vipInfos.get(i).getLevel();
                    this.discount = this.vipInfos.get(i).getDiscount();
                    this.nextVipInfo = null;
                    this.maxAmount = this.vipInfos.get(i).getMaxAmount();
                    this.minAmount = this.vipInfos.get(i).getMinAmount();
                    break;
                }
            }else{
                if(amount >= this.vipInfos.get(i).getMinAmount() && amount < this.vipInfos.get(i).getMaxAmount()){
                    this.level = this.vipInfos.get(i).getLevel();
                    this.discount = this.vipInfos.get(i).getDiscount();
                    this.nextVipInfo = this.vipInfos.get(i+1);
                    this.maxAmount = this.vipInfos.get(i).getMaxAmount();
                    this.minAmount = this.vipInfos.get(i).getMinAmount();
                    break;
                }
            }
        }
    }

    /**
     * 初始化vip信息表
     */
    public VipInfoBO initVipInfos(){
        this.vipInfos = new ArrayList<>();
        this.vipInfos.add(new VipInfoBO(0, 100, 0, 1));
        this.vipInfos.add(new VipInfoBO(100, 500, 1, 0.98));
        this.vipInfos.add(new VipInfoBO(500, 1000, 2, 0.92));
        this.vipInfos.add(new VipInfoBO(1000, 5000, 3, 0.88));
        this.vipInfos.add(new VipInfoBO(5000, 10000, 4, 0.82));
        this.vipInfos.add(new VipInfoBO(10000, 0, 5, 0.78));

        this.vipInfos.sort(Comparator.comparing(VipInfoBO::getLevel));
        return this;
    }

    public static void main(String[] args) {

    }
}
