import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup} from '@angular/forms';
import {TreasureBlueprintModel} from '../../../../../models/treasure-blueprint.model';
import {EnumsService} from '../../../../../services/enums.service';
import {TreasureBlueprintService} from '../../../../../services/treasure-blueprint.service';
import {GemstoneBlueprintModel} from '../../../../../models/gemstone-blueprint.model';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {GemstoneBlueprintFormComponent} from './gemstone-blueprint-form/gemstone-blueprint-form.component';
import {GemstoneBlueprintService} from '../../../../../services/gemstone-blueprint.service';
import {v4 as uuid} from 'uuid';
import {GemstoneModel} from '../../../../../models/gemstone.model';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-treasure-blueprint-form',
  templateUrl: './treasure-blueprint-form.component.html',
  styleUrls: ['./treasure-blueprint-form.component.css']
})
export class TreasureBlueprintFormComponent implements OnInit, OnDestroy {
  private materialSub: Subscription;
  private qualitySub: Subscription;
  private colorSub: Subscription;
  private gemListSub: Subscription;
  private treasureListSub: Subscription;

  editMode = false;

  materials: string[];
  qualityScale: string[];
  colors: string[];

  // TODO: Solve issue of repeating gemstone names and related stoneColor filter base on selected stone
  filteredGemstones: GemstoneBlueprintModel[];
  unfilteredGemstones: GemstoneBlueprintModel[];

  treasureBlueprintForm: FormGroup;
  currGemstoneForm: FormGroup;
  gemstonesForm: FormArray;

  constructor(
    private enumsService: EnumsService,
    private treasureBlueprintService: TreasureBlueprintService,
    private gemstoneBlueprintService: GemstoneBlueprintService,
    private modalService: NgbModal
  ) {
  }

  ngOnInit(): void {
    this.initTreasureBlueprintForm();

    this.materialSub = this.enumsService.getMaterials().subscribe((materials: string[]) => this.materials = materials);
    this.qualitySub = this.enumsService.getQualityLevels().subscribe((qualityLevels: string[]) => this.qualityScale = qualityLevels);
    this.colorSub = this.enumsService.getColors().subscribe((colors: string[]) => this.colors = colors);

    this.gemstoneBlueprintService.getAll();
    this.gemListSub = this.gemstoneBlueprintService.gemstoneBlueprintsList.subscribe((gemstoneBlueprints: GemstoneBlueprintModel[]) => {
      this.unfilteredGemstones = gemstoneBlueprints;
      this.filteredGemstones = gemstoneBlueprints;
    });

    this.treasureListSub = this.treasureBlueprintService.selectedTreasureBlueprint.subscribe(selectedTreasureBlueprint => this.fillForm(selectedTreasureBlueprint));
  }

  ngOnDestroy(): void {
    this.materialSub.unsubscribe();
    this.qualitySub.unsubscribe();
    this.colorSub.unsubscribe();
    this.gemListSub.unsubscribe();
    this.treasureListSub.unsubscribe();
  }

  initTreasureBlueprintForm(): void {
    this.currGemstoneForm = this.initGemstoneForm();
    this.gemstonesForm = new FormArray([]);
    this.treasureBlueprintForm = new FormGroup(
      {
        id: new FormControl(null),
        name: new FormControl(null),
        publicDescription: new FormControl(null),
        privateDescription: new FormControl(null),
        weight: new FormControl(null),
        currGem: this.currGemstoneForm,
        gemstones: this.gemstonesForm,
        // TODO: Material could be array in same regard as Gemstone
        productQuality: new FormControl(null),
        material: new FormControl(null),
        materialWeight: new FormControl(null),
        // TODO: Consider hiding/removing etc. if any gemstone or material is present
        goldCoins: new FormControl(null),
        silverCoins: new FormControl(null),
        copperCoins: new FormControl(null)
      }
    );
  }

  initGemstoneForm(): FormGroup {
    return new FormGroup({
      id: new FormControl(null),
      stone: new FormControl(null),
      stoneColor: new FormControl(null),
      stoneWeight: new FormControl(null),
      stoneCount: new FormControl(null),
      stonePolished: new FormControl(false),
      blueprintId: new FormControl(null)
    });
  }

  onSubmit(): void {}

  save(): void {
    this.gemstonesForm.controls.forEach((gemstoneFormGroup: FormGroup) => {
      gemstoneFormGroup.patchValue({id: uuid()});
    });
    this.treasureBlueprintForm.patchValue({id: uuid()});
    this.treasureBlueprintService.save(this.treasureBlueprintForm.value);
    this.clearForm();
  }

  update(): void {
    this.treasureBlueprintService.update(this.treasureBlueprintForm.value);
    this.clearForm();
  }

  delete(): void {
    this.treasureBlueprintService.delete(this.treasureBlueprintForm.get('id').value);
    this.clearForm();
  }

  fillForm(treasureBlueprint: TreasureBlueprintModel) {
    this.clearForm();
    this.editMode = true;
    this.treasureBlueprintForm.patchValue(treasureBlueprint);
    treasureBlueprint.gemstones.forEach((gemstone: GemstoneModel) => {
      this.currGemstoneForm.patchValue(gemstone);
      this.addGemstone();
    });
  }

  clearForm() {
    this.editMode = false;
    this.treasureBlueprintForm.reset();
    this.currGemstoneForm.reset();
    this.gemstonesForm.clear();
  }

  addGemstone() {
    const stone = this.findGemstone(this.currGemstoneForm.get('blueprintId').value).name;
    this.currGemstoneForm.get('stone').setValue(stone);
    if (this.currGemstoneForm.get('id').value === null) {
      this.currGemstoneForm.get('id').setValue(uuid()); }

    this.gemstonesForm.push(this.initGemstoneForm());
    this.gemstonesForm.at(this.gemstonesForm.length - 1).patchValue(this.currGemstoneForm.value);

    this.currGemstoneForm.reset();
  }

  removeGemStone(index: number) {
    this.gemstonesForm.removeAt(index);
  }

  findGemstone(id: string): GemstoneBlueprintModel {
    let stone: GemstoneBlueprintModel = null;
    this.unfilteredGemstones.forEach((gemstoneBlueprint: GemstoneBlueprintModel) => {
      if (gemstoneBlueprint.id === id) {
        stone = gemstoneBlueprint;
      }
    });
    return stone;
  }

  getGemStones() {
    return this.gemstonesForm.controls;
  }

  addGemstoneBlueprint() {
    this.modalService.open(GemstoneBlueprintFormComponent);
  }

  filterGemstoneBlueprints() {
    const currColor = this.currGemstoneForm.get('stoneColor').value;
    this.currGemstoneForm.get('stone').reset();
    if (currColor === '') {
      this.filteredGemstones = this.unfilteredGemstones;
    } else {
      this.filteredGemstones = this.unfilteredGemstones.filter((gemStoneBlueprint: GemstoneBlueprintModel) => gemStoneBlueprint.color === currColor);
    }
  }
}
