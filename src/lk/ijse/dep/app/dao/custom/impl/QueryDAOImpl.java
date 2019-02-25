package lk.ijse.dep.app.dao.custom.impl;


import lk.ijse.dep.app.dao.custom.QueryDAO;
import lk.ijse.dep.app.entity.CustomEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class QueryDAOImpl implements QueryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<CustomEntity> findOrderDetailsWithItemDescriptions(String orderId) throws Exception {
        Query query = getSession().createQuery("select e.orderDetailPK.orderId,o.date,o.customer.id,o.customer.name,e.qty*e.unitPrice from OrderDetail e INNER JOIN e.item a INNER JOIN e.order o where e.orderDetailPK.orderId=?1")
                .setParameter(1, orderId);
        List<Object[]> list = query.list();
        ArrayList<CustomEntity> customEntityArrayList = new ArrayList<>();

        for(Object[] arr : list){
            customEntityArrayList.add(new CustomEntity((String)arr[0],(Date)arr[1],(String)arr[2],(String)arr[3],(double)arr[4]));
            System.out.println( Arrays.toString(arr)+"  ad@mia  "+arr[4]);
        }
        return customEntityArrayList;
    }

    @Override
    public List<CustomEntity> findAllOrdersWithCustomerNameAndTotal() throws Exception {

        Query query = getSession().createQuery("select e.orderDetailPK.orderId,o.date,o.customer.id,o.customer.name,e.qty*e.unitPrice from OrderDetail e INNER JOIN e.item a INNER JOIN e.order o");
        List<Object[]> list = query.list();
        ArrayList<CustomEntity> customEntityArrayList = new ArrayList<>();

        for(Object[] arr : list){
            customEntityArrayList.add(new CustomEntity((String)arr[0],(Date)arr[1],(String)arr[2],(String)arr[3],(double)arr[4]));
          System.out.println( Arrays.toString(arr)+"  ad@mia  "+arr[4]);
        }
        return customEntityArrayList;
    }


    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
