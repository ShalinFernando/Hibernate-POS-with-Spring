package lk.ijse.dep.app.business.custom.impl;

import lk.ijse.dep.app.business.Converter;
import lk.ijse.dep.app.business.custom.ManageItemsBO;
import lk.ijse.dep.app.dao.custom.ItemDAO;
import lk.ijse.dep.app.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManageItemsBOImpl implements ManageItemsBO {
    private ItemDAO itemDAO;

    @Autowired
    public ManageItemsBOImpl(ItemDAO itemDAO) {

        this.itemDAO = itemDAO;
    }

    public List<ItemDTO> getItems() throws Exception {
        List<ItemDTO> itemDTOS = itemDAO.findAll().map(Converter::<ItemDTO>getDTOList).get();
        return itemDTOS;
    }

    public void createItem(ItemDTO dto) throws Exception {
        itemDAO.save(Converter.getEntity(dto));
    }

    @Override
    public void updateItem(ItemDTO dto) throws Exception {
        itemDAO.update(Converter.getEntity(dto));
    }

    @Override
    public void deleteItem(String code) throws Exception {
        itemDAO.delete(code);
    }

    @Override
    public ItemDTO findItem(String itemCode) throws Exception {
        ItemDTO itemDTO = itemDAO.find(itemCode).map(Converter::<ItemDTO>getDTO).orElse(null);
        return itemDTO;
    }

}
