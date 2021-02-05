package ru.baib.shedule;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import ru.baib.parse.Parse;
import ru.baib.store.Store;

public interface Grab {
    void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException;
}
