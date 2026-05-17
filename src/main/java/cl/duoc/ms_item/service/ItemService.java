package cl.duoc.ms_item.service;

import cl.duoc.ms_item.dto.ItemResponseDto;
import cl.duoc.ms_item.enums.Rarity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {

    List<ItemResponseDto> findAll();
    ItemResponseDto findById(Long id);
    ItemResponseDto findByName(String name);
    List<ItemResponseDto> findByLevel(int level);
    ItemResponseDto updateDescription(Long id, String description);
    ItemResponseDto updatePrice(Long id, int price);
    ItemResponseDto updateRarity(Long id, Rarity rarity);
    void deleteById(Long id);
}
