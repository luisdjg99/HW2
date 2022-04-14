package dataStructure;

import java.util.Arrays;
import java.util.Random;

public class RandomArray {

    public RandomArray(){

        int arraySize = 500000;
        int maxGiftIdValue = 700000;

        Random rd = new Random();
        int[] arr = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            arr[i] = rd.nextInt(maxGiftIdValue);

        }
        Arrays.sort(arr);
    }
}
