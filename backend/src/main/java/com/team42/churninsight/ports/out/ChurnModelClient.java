package com.team42.churninsight.ports.out;

import com.team42.churninsight.domain.CustomerData;
import com.team42.churninsight.domain.Prediction;

public interface ChurnModelClient {
    Prediction predict(CustomerData customerData);
}