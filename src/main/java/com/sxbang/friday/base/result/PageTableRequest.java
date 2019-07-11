package com.sxbang.friday.base.result;

import lombok.Data;

import java.io.Serializable;
/**
 * @author kaneki
 */

@Data
public class PageTableRequest implements Serializable {

    private static final long serialVersionUID = -7800374527556164106L;

    private Integer page;
    private Integer limit;
    private Integer offset;

    public void countOffset() {
        if (null == this.page || null == this.limit) {
            this.offset = 0;
            return;
        }
        this.offset = (this.page - 1) * limit;
    }
}
