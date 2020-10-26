import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SoliqTestModule } from '../../../test.module';
import { AcademicDegreeUpdateComponent } from 'app/entities/academic-degree/academic-degree-update.component';
import { AcademicDegreeService } from 'app/entities/academic-degree/academic-degree.service';
import { AcademicDegree } from 'app/shared/model/academic-degree.model';

describe('Component Tests', () => {
  describe('AcademicDegree Management Update Component', () => {
    let comp: AcademicDegreeUpdateComponent;
    let fixture: ComponentFixture<AcademicDegreeUpdateComponent>;
    let service: AcademicDegreeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoliqTestModule],
        declarations: [AcademicDegreeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AcademicDegreeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AcademicDegreeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AcademicDegreeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AcademicDegree(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AcademicDegree();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
