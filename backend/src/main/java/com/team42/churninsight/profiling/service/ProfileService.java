package com.team42.churninsight.profiling.service;

import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.profiling.enums.ProfileType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProfileService {

    private double CalculateOnlinePercentage(PredictionRequest request) {

        Integer online = request.onlinePurchases();
        Integer inStore = request.inStorePurchases();

        int totalPurchase =  online + inStore;

        if(totalPurchase == 0) {
            return 0.0;
        }

        return (online *100.0)/totalPurchase;
    }

    public ProfileType identifyProfile(PredictionRequest request) {

        BigDecimal discountUsage = request.avgDiscountUsed();
        BigDecimal totalSales =  request.totalSales();

        double onlinePercentage = CalculateOnlinePercentage(request);

        //Revisar valor a comparar para discountUsage
        if( discountUsage.compareTo(BigDecimal.valueOf(5) ) > 0
                && onlinePercentage < 60 ) {

            return ProfileType.IN_STORE_DEAL_HUNTER;
        }

        if ( totalSales.compareTo(BigDecimal.valueOf(10000) ) > 0
                && discountUsage.compareTo(BigDecimal.valueOf(0.30)) >=0
                && discountUsage.compareTo(BigDecimal.valueOf(0.50)) <=0
                && onlinePercentage > 65.0 ) {

            return ProfileType.HIGH_VALUE_DISCOUNT_ONLINE;
        }

        return ProfileType.ESSENTIAL_MODERATE_BUYER;

    }
}
