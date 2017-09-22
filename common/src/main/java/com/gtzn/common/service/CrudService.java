/**
 *
 */
package com.gtzn.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gtzn.common.persistence.CrudDao;
import com.gtzn.common.persistence.DataEntity;
import com.gtzn.common.persistence.Page;
import com.gtzn.common.persistence.Pager;
import com.gtzn.modules.sys.entity.User;

/**
 * Service基类
 *
 * @author gtzn
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 带权限的查询列表数据
     *
     * @param entity
     * @param user
     * @param officeAlias
     * @param userAlias
     * @return
     */
    public List<T> findList(T entity, User user, String officeAlias, String userAlias) {
        entity.getSqlMap().put("dsf", dataScopeFilter(user, officeAlias, userAlias));
        return dao.findList(entity);
    }

    public List<T> findAllList(T entity) {
        return dao.findAllList(entity);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity
     * @return
     */
    @Deprecated
    public Page<T> findPage(Page<T> page, T entity) {
        //entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }

    public Pager<T> findPage(Pager<T> pager, T entity) {
        entity.setPager(pager);
        pager.setList(dao.findPage(entity));
        pager.setRecords(dao.findCount(entity));
        return pager;
    }

    /**
     * 带数据权限的分页查询
     *
     * @param pager       分页对象
     * @param entity      实体对象
     * @param user        使用 WebUtil.getUser()
     * @param officeAlias 机构表别名，多个用“,”逗号隔开，。
     * @param userAlias   用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
     * @return
     */
    public Pager<T> findPage(Pager<T> pager, T entity, User user, String officeAlias, String userAlias) {
        entity.setPager(pager);
        entity.getSqlMap().put("dsf", dataScopeFilter(user, officeAlias, userAlias));
        pager.setList(dao.findPage(entity));
        pager.setRecords(dao.findCount(entity));
        return pager;
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void save(T entity) {
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            dao.insertSelective(entity);
        } else {
            entity.preUpdate();
            dao.updateSelective(entity);
        }
    }

    /**
     * 更新数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public int update(T entity) {
        entity.preUpdate();
        return dao.update(entity);
    }


    /**
     * 条件更新
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public int updateSelective(T entity) {
        entity.preUpdate();
        return dao.updateSelective(entity);
    }

    /**
     * 插入数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public int insert(T entity) {
        entity.preInsert();
        return dao.insert(entity);
    }


    /**
     * 条件插入
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public int insertSelective(T entity) {
        entity.preInsert();
        return dao.insertSelective(entity);
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public int delete(T entity) {
        return dao.delete(entity);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    @Transactional(readOnly = false)
    public int delete(String id) {
        return dao.delete(id);
    }

    /**
     * 批量删除数据
     *
     * @param idList
     */
    @Transactional(readOnly = false)
    public int batchDelete(String[] idList) {
        return dao.batchDelete(idList);
    }

    /**
     * 批量插入数据
     *
     * @param list
     */
    @Transactional(readOnly = false)
    public int batchInsert(List<T> list) {
        return dao.batchInsert(list);
    }

    /**
     * 执行自定义SQL
     *
     * @param sql
     */
    @Transactional(readOnly = false)
    public int executeUpdate(String sql) {
        return dao.executeUpdate(sql);
    }
}
