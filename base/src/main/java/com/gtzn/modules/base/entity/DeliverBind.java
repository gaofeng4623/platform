package com.gtzn.modules.base.entity;


import com.gtzn.common.persistence.DataEntity;
import com.gtzn.modules.sys.entity.Office;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 */
public class DeliverBind extends DataEntity<DeliverBind> {

    private static final long serialVersionUID = 1L;
    private Office office;
    private String workMachIp;
    private String deliverMachIp;
    private int page;
    private int rows;

    public DeliverBind() {
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getWorkMachIp() {
        return workMachIp;
    }

    public void setWorkMachIp(String workMachIp) {
        this.workMachIp = workMachIp;
    }

    public String getDeliverMachIp() {
        return deliverMachIp;
    }

    public void setDeliverMachIp(String deliverMachIp) {
        this.deliverMachIp = deliverMachIp;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("office", office)
                .append("workMachIp", workMachIp)
                .append("deliverMachIp", deliverMachIp)
                .append("page", page)
                .append("rows", rows)
                .toString();
    }
}
