package lk.ijse.dep.app.dao.custom.impl;

import lk.ijse.dep.app.dao.CrudDAOImpl;
import lk.ijse.dep.app.dao.custom.OrderDetailDAO;
import lk.ijse.dep.app.entity.OrderDetail;
import lk.ijse.dep.app.entity.OrderDetailPK;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail, OrderDetailPK> implements OrderDetailDAO {
    @Override
    public List<OrderDetail> find(String orderId) throws Exception {
        List<OrderDetail> list1 = getSession().createQuery("SELECT o FROM OrderDetail o where o.order.id=?1", OrderDetail.class)
                .setParameter(1,orderId)
                .list();

        for (OrderDetail orderDetail : list1) {
            System.out.println(orderDetail.getOrder()+"imal"+orderDetail.getQty());
        }
        return list1;
    }
}
