package org.expense.tracker.services;

import org.expense.tracker.data.models.Date;

import java.math.BigDecimal;
import java.util.List;

public interface HistoryService<T>{
        T addRecord(String userId, String recordId, Date date, BigDecimal amount, String description);

        void removeRecord(String recordId, String userId);

        List<T> viewRecords(String userId);

        void removeAllRecords(String userId);
}
