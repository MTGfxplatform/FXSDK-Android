package com.mintegral.detailroi.report;

import java.util.ArrayList;
import java.util.List;

public class BatchController {
    private List batchList = new ArrayList();
    private BatchController(){}
    private static class Holder{
        private static BatchController instance = new BatchController();
    }

    public static BatchController getInstance() {
        return BatchController.Holder.instance;
    }


    public void insertEvent(){}

    public void getAllEvent(){}

}
