package lk.ijse.dep.app.business.custom.impl;

import lk.ijse.dep.app.business.Converter;
import lk.ijse.dep.app.business.custom.ManageOrdersBO;
import lk.ijse.dep.app.dao.custom.*;
import lk.ijse.dep.app.dto.CustomerDTO;
import lk.ijse.dep.app.dto.OrderDTO;
import lk.ijse.dep.app.dto.OrderDTO2;
import lk.ijse.dep.app.dto.OrderDetailDTO;
import lk.ijse.dep.app.entity.CustomEntity;
import lk.ijse.dep.app.entity.Item;
import lk.ijse.dep.app.entity.Order;
import lk.ijse.dep.app.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ManageOrdersBOImpl implements ManageOrdersBO {
    private OrderDAO orderDAO;
    private OrderDetailDAO orderDetailDAO;
    private ItemDAO itemDAO;
    private QueryDAO queryDAO;
    private CustomerDAO customerDAO;

    @Autowired
    public ManageOrdersBOImpl(OrderDAO orderDAO, OrderDetailDAO orderDetailDAO, ItemDAO itemDAO, QueryDAO queryDAO, CustomerDAO customerDAO) {
        this.orderDAO = orderDAO;
        this.orderDetailDAO = orderDetailDAO;
        this.itemDAO = itemDAO;
        this.queryDAO = queryDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public List<OrderDTO2> getOrdersWithCustomerNamesAndTotals() throws Exception {
        List<CustomEntity> alt = queryDAO.findAllOrdersWithCustomerNameAndTotal();

        ArrayList<OrderDTO2> objects = new ArrayList<>();
        for (CustomEntity customEntity : alt) {
            objects.add(new OrderDTO2(customEntity.getOrderId(), customEntity.getOrderDate().toLocalDate(), customEntity.getCustomerId(), customEntity.getCustomerName(), customEntity.getTotal()));
        }
        return objects;
    }

    @Override
    public List<OrderDTO> getOrders() throws Exception {
        List<OrderDTO> orderDTOS = orderDAO.findAll().map(Converter::<OrderDTO>getDTOList).get();
        return orderDTOS;
    }

    @Override
    public String generateOrderId() throws Exception {
        String count = orderDAO.count() + 1 + "";
        return count;
    }

    @Override
    public void createOrder(OrderDTO dto) throws Exception {
        orderDAO.save(new Order(dto.getId(), Date.valueOf(dto.getDate()), Converter.getEntity(dto.getCustomerId())));


        for (OrderDetailDTO detailDTO : dto.getOrderDetailDTOS()) {
            orderDetailDAO.save(new OrderDetail(dto.getId(),
                    detailDTO.getCode(), detailDTO.getQty(), detailDTO.getUnitPrice()));


            Item item = itemDAO.find(detailDTO.getCode()).get();
            int qty = item.getQtyOnHand() - detailDTO.getQty();
            item.setQtyOnHand(qty);
            itemDAO.update(item);

        }
    }

    @Override
    public OrderDTO findOrder(String orderId) throws Exception {
        CustomerDTO customerDTO;
        List<OrderDetailDTO> dtoList = new ArrayList<>();

        List<CustomEntity> odwtid = queryDAO.findOrderDetailsWithItemDescriptions(orderId);
        String cusID = null;
        for (CustomEntity customEntity : odwtid) {
            cusID = customEntity.getCustomerId();
        }
        OrderDTO orderDTO = null;


        customerDTO = customerDAO.find(cusID).map(Converter::<CustomerDTO>getDTO).orElse(null);

        List<OrderDetail> orderDetails = orderDetailDAO.find(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            System.out.println(orderDetail.getOrder().getId() + " " + orderDetail.getItem().getDescription() + " " + orderDetail.getQty() + " " + orderDetail.getUnitPrice());
            dtoList.add(new OrderDetailDTO(orderDetail.getItem().getCode(), orderDetail.getItem().getDescription(), orderDetail.getQty(), orderDetail.getUnitPrice()));
        }

        for (CustomEntity customEntity : odwtid) {
            //////////////////////////////////////////////////////////////////////////////////////////////////

            orderDTO = new OrderDTO(customEntity.getOrderId(), customEntity.getOrderDate().toLocalDate(), customerDTO, dtoList);
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }

        return orderDTO;
    }

}
