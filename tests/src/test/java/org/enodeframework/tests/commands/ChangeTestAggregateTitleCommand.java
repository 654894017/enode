package org.enodeframework.tests.commands;

import org.enodeframework.commanding.Command;

public class ChangeTestAggregateTitleCommand extends Command {
    public String Title;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
