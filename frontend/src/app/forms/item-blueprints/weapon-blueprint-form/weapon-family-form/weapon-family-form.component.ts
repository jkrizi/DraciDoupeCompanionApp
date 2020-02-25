import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {EnumsService} from '../../../../services/enums.service';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {WeaponFamilyService} from '../../../../services/weapon-family.service';
import {v4 as uuid} from 'uuid';
import {WeaponFamilyModel} from '../../../../models/weapon-family.model';

@Component({
  selector: 'app-weapon-family-form',
  templateUrl: './weapon-family-form.component.html',
  styleUrls: ['./weapon-family-form.component.css']
})
export class WeaponFamilyFormComponent implements OnInit {
  // Component controls
  isMelee = true;
  isSingleHanded = true;

  // Backend enums
  damageTypes: string[];
  weightCategories: string[];

  // Form parts
  weaponFamilyForm: FormGroup;
  damageTypeForm: FormArray;

  constructor(
    public activeModal: NgbActiveModal,
    private enumsService: EnumsService,
    private weaponFamilyService: WeaponFamilyService
  ) {}

  // TODO: Add validations
  ngOnInit() {
    this.damageTypeForm =  new FormArray([]);
    this.weaponFamilyForm = new FormGroup({
      id: new FormControl(uuid()),
      name: new FormControl('', Validators.required),
      weaponType: new FormGroup({
        melee: new FormControl(true),
        singleHanded: new FormControl(true),
        weightCategory: new FormControl(''),
        damageType: this.damageTypeForm
      }),
    });

    this.enumsService.getWeaponDamageTypes().subscribe((damageTypes: string[]) => this.damageTypes = damageTypes);
    this.enumsService.getWeightCategories().subscribe((weightCategories: string[]) => this.weightCategories = weightCategories);
  }

  onSubmit(): void {}

  save() {
    const weaponFamily: WeaponFamilyModel = this.weaponFamilyForm.value;
    this.weaponFamilyService.save(weaponFamily);
    this.close();
  }

  close(): void {
    this.activeModal.close('Close click');
  }

  // TODO: Make added controls unmodifiable, also do not add already added damage types
  addDamageType(selectedValue: string) {
    this.damageTypeForm.push(new FormControl(selectedValue));
  }

  getDamageTypes() {
    return this.damageTypeForm.controls;
  }

  removeDamageType(index: number) {
    this.damageTypeForm.removeAt(index);
  }
}
