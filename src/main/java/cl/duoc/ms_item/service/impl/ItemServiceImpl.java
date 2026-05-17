package cl.duoc.ms_item.service.impl;

import cl.duoc.ms_item.dto.ItemRequestDto;
import cl.duoc.ms_item.dto.ItemResponseDto;
import cl.duoc.ms_item.enums.ItemType;
import cl.duoc.ms_item.enums.Rarity;
import cl.duoc.ms_item.model.Item;
import cl.duoc.ms_item.repository.ItemRepository;
import cl.duoc.ms_item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    private ItemResponseDto toDto(Item item){
        return new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getRequiredLevel(),
                item.getItemType(),
                item.getRarity()
        );
    }

    private Item toEntity(ItemRequestDto dto){
        return new Item(null, dto.getName(), dto.getDescription(), dto.getPrice(), dto.getRequiredLevel(), dto.getItemType(), dto.getRarity());
    }

    @Override
    public ItemResponseDto findByName(String name) {
        return repository.findByName(name).map(this::toDto).orElseThrow(() -> new RuntimeException("Nombre no encontrado"));
    }

    @Override
    public List<ItemResponseDto> findByLevel(int level) {
        List<Item> items = repository.findByRequiredLevel(level);

        if(items.isEmpty()) {
            throw new RuntimeException("Ningun item de nivel " + level);
        }
        return items.stream().map(this::toDto).toList();
    }

    @Override
    public ItemResponseDto updateDescription(Long id, String description) {
        Item item = repository.findById(id).orElseThrow(() -> new RuntimeException("Item con ID " + id + " no existe"));
        item.setDescription(description);
        return toDto(repository.save(item));
    }

    @Override
    public ItemResponseDto updatePrice(Long id, int price) {
        Item item = repository.findById(id).orElseThrow(() -> new RuntimeException("Item con ID " + id + " no existe"));
        if (price < 0) throw new RuntimeException("El valor debe ser mayor o igual a 0");
        item.setPrice(price);
        return toDto(repository.save(item));
    }

    @Override
    public ItemResponseDto updateRarity(Long id, Rarity rarity) {
        Item item = repository.findById(id).orElseThrow(() -> new RuntimeException("Item con ID " + id + " no existe"));
        item.setRarity(rarity);
        return toDto(repository.save(item));
    }

    @Override
    public List<ItemResponseDto> findAll(){
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public ItemResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElseThrow(() -> new RuntimeException("Item con ID " + id + " no existe"));
    }

    @Override
    public List<ItemResponseDto> findByRarity(String rarity) {
        Rarity rarityEnum = Rarity.valueOf(rarity.toUpperCase());
        return repository.findByRarity(rarityEnum).stream().map(this::toDto).toList();
    }

    @Override
    public List<ItemResponseDto> findByType(String itemType) {
        ItemType typeEnum = ItemType.valueOf(itemType.toUpperCase());
        return repository.findByItemType(typeEnum).stream().map(this::toDto).toList();
    }

    @Override
    public ItemResponseDto createItem(ItemRequestDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("El nombre del item es obligatorio");
        }
        if (dto.getPrice() < 0) {
            throw new RuntimeException("El valor debe ser mayor o igual a 0");
        }
        if (dto.getRequiredLevel() < 0) {
            throw new RuntimeException("El nivel requerido no puede ser negativo");
        }

        Item savedItem = repository.save(this.toEntity(dto));
        return this.toDto(savedItem);
    }

    @Override
    public ItemResponseDto updateItem(Long id, ItemRequestDto dto) {
        Item existingItem = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item con ID " + id + " no existe"));

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("El nombre del item es obligatorio");
        }
        if (dto.getPrice() < 0) {
            throw new RuntimeException("El valor debe ser mayor o igual a 0");
        }
        if (dto.getRequiredLevel() < 0) {
            throw new RuntimeException("El nivel requerido no puede ser negativo");
        }

        existingItem.setName(dto.getName());
        existingItem.setDescription(dto.getDescription());
        existingItem.setPrice(dto.getPrice());
        existingItem.setRequiredLevel(dto.getRequiredLevel());
        existingItem.setItemType(dto.getItemType());
        existingItem.setRarity(dto.getRarity());

        return this.toDto(repository.save(existingItem));
    }

    @Override
    public void deleteItem(Long id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("Item con ID " + id + " no existe");
        }
        repository.deleteById(id);
    }


}