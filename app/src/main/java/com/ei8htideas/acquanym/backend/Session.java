package com.ei8htideas.acquanym.backend;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Henry on 9/07/2018.
 */

public class Session {
    private Details user;

    private Set<Details> checkedUsers;

    public Session(Details user) {
        this.user = user;
        checkedUsers = new HashSet<>();
    }

    public void addCheckedUser(Details user) {
        checkedUsers.add(user);
    }

    public boolean userChecked(Details user) {
        return checkedUsers.contains(user);
    }
}
