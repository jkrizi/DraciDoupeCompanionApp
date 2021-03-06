package net.homecredit.trainee.drd.entity.inventory;

import net.homecredit.trainee.drd.entity.shop.ItemType;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Equipment {

    @Id
    private UUID id;
    private String name;
    private String description;
    private int weight;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_unit_id")
    private StorageUnit storage;
    @Enumerated(value = EnumType.STRING)
    private ItemType itemType;
    private UUID blueprintId;

    /*@Column(name = "ITEM_TYPE")
    private ItemType itemType;*/
    /*@Column(name = "BLUEPRINT_ID")
    private UUID blueprintId;*/

    public Equipment() {
    }

    /**
     * Used for initialization of base Storage Units
     * These may not be included in other Storage Units and have no CoinValue
     */
    public Equipment(String name, String description, int weight, ItemType itemType) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.itemType = itemType;
    }

    public Equipment(ItemBlueprint itemBlueprint, ItemType itemType) {
        this.id = UUID.randomUUID();
        this.itemType = itemType;
        this.blueprintId = itemBlueprint.getId();
        this.name = itemBlueprint.getName();
        this.description = itemBlueprint.getPublicDescription();
        this.weight = itemBlueprint.getWeight();
    }

    public Equipment(ItemBlueprint itemBlueprint, StorageUnit storage, ItemType itemType) {
        this.id = UUID.randomUUID();
        this.blueprintId = itemBlueprint.getId();
        this.name = itemBlueprint.getName();
        this.description = itemBlueprint.getPublicDescription();
        this.weight = itemBlueprint.getWeight();
        this.storage = storage;
        this.itemType = itemType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public StorageUnit getStorage() {
        return storage;
    }

    public void setStorage(StorageUnit storage) {
        this.storage = storage;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public UUID getBlueprintId() {
        return blueprintId;
    }

    public void setBlueprintId(UUID blueprintId) {
        this.blueprintId = blueprintId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipment)) return false;
        Equipment equipment = (Equipment) o;
        return id.equals(equipment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", storage=" + storage +
                ", itemType=" + itemType +
                ", blueprintId=" + blueprintId +
                '}';
    }
}
