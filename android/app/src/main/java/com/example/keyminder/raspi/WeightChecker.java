package com.example.keyminder.raspi;

import com.example.keyminder.network.HttpGetTask;

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
            String response = getWeightTask.execute().get();
            // weightをgetしてDoubleにparseする処理
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return weight;
    }
}
