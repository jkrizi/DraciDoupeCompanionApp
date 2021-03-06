package net.homecredit.trainee.drd.entity.character.person.profession.warrior;

import net.homecredit.trainee.drd.entity.character.beast.BeastBlueprint;
import net.homecredit.trainee.drd.entity.inventory.weapon.WeaponBlueprint;
import net.homecredit.trainee.drd.entity.character.person.Person;
import net.homecredit.trainee.drd.entity.character.person.profession.ProfessionKnowHow;
import net.homecredit.trainee.drd.entity.inventory.weapon.Weapon;

import javax.persistence.*;
import java.util.Collection;
import java.util.Map;

@Entity
public class WarriorKnowHow extends ProfessionKnowHow {

    @ElementCollection
    @CollectionTable(name = "ENCOUNTER", joinColumns = @JoinColumn(name = "WARRIOR_KNOWHOW_ID"))
    @Column(name = "NR_OF_ENCOUNTERS")
    private Map<BeastBlueprint, Integer> beastsEncountered;

    @ElementCollection
    @CollectionTable(name = "COHERENCE", joinColumns = @JoinColumn(name = "WARRIOR_KNOWHOW_ID"))
    @Column(name = "COMMON_LEVELS")
    private Map<Person, Integer> commonLevels;

    @ElementCollection
    @CollectionTable(name = "WEAPON_PROFICIENCY", joinColumns = @JoinColumn(name = "WARRIOR_KNOWHOW_ID"))
    @Column(name = "EXPERIENCE_EARNED")
    private Map<Weapon, Integer> experienceEarned;

    @OneToMany(mappedBy = "warriorKnowHow")
    private Collection<SwordMoveBook> swordPlayMoves;

    @ElementCollection
    @CollectionTable(name = "WEAPONTYPE_PROFICIENCY", joinColumns = @JoinColumn(name = "WARRIOR_KNOWHOW_ID"))
    @Column(name = "NR_OF_LEVELS_USED")
    private Map<WeaponBlueprint, Integer> levelsUsed;

}
