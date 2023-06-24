package com.example.keyminder.raspi;

import android.util.Log;

import com.example.keyminder.network.HttpGetTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WeightChecker {

    private double threshold;

    public WeightChecker(double threshold) {
        this.threshold = threshold;
    }

    public void changeThreshold(double newThreshold) {
        threshold = newThreshold;
    }

    public boolean isCloseToDetectedWeight(double weight) {
        double detectedWeight = getWeight();
        if (Math.abs(detectedWeight - weight) < threshold) {
            return true;
        }
        return false;
    }

    public Double getWeight() {
        Double weight = null;
        GetWeightTask getWeightTask = new GetWeightTask();
        try {
            // weightをgetしてDoubleにparseする処理
            String response = getWeightTask.execute().get();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> json;
            json = objectMapper.readValue(response, new TypeReference<Map<String,Object>>(){});
            Object v = json.get("weight");
            if (v.getClass() == Integer.class) {
                weight = ((Integer) v).doubleValue();
            } else if (v.getClass() == Double.class) {
                weight = (Double) v;
            } else if (v.getClass() == String.class) {
                weight = Double.parseDouble((String) v);
            } else {
                Log.d("Error", "JSON TYPE ERROR");
            }

            // 単にStringで返す場合なら
            // weight = Double.parseDouble((response));

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return weight;
    }
}
