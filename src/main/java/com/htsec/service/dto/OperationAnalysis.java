package com.htsec.service.dto;

import java.math.BigDecimal;

/**
 * Created by bernard on 2017/9/24.
 */
public class OperationAnalysis {
   private String stockSelection;
   private String timeSelection;
   private String tradeSuccessRate;
   private String tradeTime;
   private String profitSecCount;

   public String getStockSelection() {
      return stockSelection;
   }

   public void setStockSelection(String stockSelection) {
      this.stockSelection = stockSelection;
   }

   public String getTimeSelection() {
      return timeSelection;
   }

   public void setTimeSelection(String timeSelection) {
      this.timeSelection = timeSelection;
   }

   public String getTradeSuccessRate() {
      return tradeSuccessRate;
   }

   public void setTradeSuccessRate(String tradeSuccessRate) {
      this.tradeSuccessRate = tradeSuccessRate;
   }

   public String getTradeTime() {
      return tradeTime;
   }

   public void setTradeTime(String tradeTime) {
      this.tradeTime = tradeTime;
   }

   public String getProfitSecCount() {
      return profitSecCount;
   }

   public void setProfitSecCount(String profitSecCount) {
      this.profitSecCount = profitSecCount;
   }
}
