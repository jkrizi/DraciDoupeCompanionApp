package net.homecredit.trainee.drd.repository.character;

import net.homecredit.trainee.drd.entity.character.beast.BeastBlueprint;
import net.homecredit.trainee.drd.util.comparator.BeastBlueprintComparatorByAttributes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Repository
public class BeastBlueprintRepository {

    private EntityManager entityManager;

    public BeastBlueprintRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(BeastBlueprint blueprint) {
        entityManager.persist(blueprint);
    }

    public List<BeastBlueprint> findAll() {
        TypedQuery<BeastBlueprint> query = entityManager.createQuery(
                "select distinct bb " +
                        "from BeastBlueprint bb " +
                        "join fetch bb.abilityMap " +
                        "join fetch bb.sizes " +
                        "join fetch bb.vulnerabilities " +
                        "left join fetch bb.armorBlueprints bab left join fetch bab.armorBlueprint ab left join fetch  ab.coverage " +
                        "left join fetch bb.weaponBlueprints bwb left join fetch bwb.weaponBlueprint wb left join fetch wb.weaponFamily wf left join fetch wf.weaponType.damageType " +
                        "left join fetch bb.treasureBlueprints btb left join fetch btb.treasureBlueprint tb left join fetch tb.gemstones g left join fetch g.gemstoneBlueprint " +
                        "left join fetch bb.goodsBlueprints bgb left join fetch bgb.goodsBlueprint", BeastBlueprint.class);
        return query.getResultList();
    }

    public void deleteAll() {
        for (BeastBlueprint beastBlueprint : findAll()) {
            entityManager.remove(beastBlueprint);
        }
    }

    public boolean containsBlueprint(BeastBlueprint beastBlueprint) {
        BeastBlueprintComparatorByAttributes comparator = new BeastBlueprintComparatorByAttributes();
        for (BeastBlueprint beastBlueprint1 : findAll()) {
            if (comparator.compare(beastBlueprint, beastBlueprint1) == 0) {
                return true;
            }
        }
        return false;
    }

    public void update(BeastBlueprint beastBlueprint) {
        entityManager.merge(beastBlueprint);
    }

    public void delete(UUID id) {
        entityManager.remove(entityManager.find(BeastBlueprint.class, id));
    }
}
