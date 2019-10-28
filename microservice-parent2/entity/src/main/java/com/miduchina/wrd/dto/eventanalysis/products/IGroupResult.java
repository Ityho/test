/*    */ package com.miduchina.wrd.dto.eventanalysis.products;
/*    */ 
/*    */ import lombok.Data;

import java.util.List;
/*    */ 
@Data
/*    */ public class IGroupResult implements java.io.Serializable{
/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
/*  7 */   private String groupValue = "";
/*    */   
/*    */   private int total;
/*    */   
/*    */   private double calculatedValue;
/*    */   
/*    */   private List<IGroupResult> subGroups;
}