package cl.duoc.ms_item.repository;

import cl.duoc.ms_item.dto.ItemResponseDto;
import cl.duoc.ms_item.enums.ItemType;
import cl.duoc.ms_item.model.Item;
import cl.duoc.ms_item.enums.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByRarity(Rarity rarity);
    List<Item> findByItemType(ItemType itemType);
    List<Item> findByRequiredLevel(int requiredLevel);
    List<Item> findByRequiredLevelLessThanEqual(int level);
    Optional<Item> findByName(String name);
}

