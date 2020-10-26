import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AcademicDegreeService } from 'app/entities/academic-degree/academic-degree.service';
import { IAcademicDegree, AcademicDegree } from 'app/shared/model/academic-degree.model';
import { Degree } from 'app/shared/model/enumerations/degree.model';

describe('Service Tests', () => {
  describe('AcademicDegree Service', () => {
    let injector: TestBed;
    let service: AcademicDegreeService;
    let httpMock: HttpTestingController;
    let elemDefault: IAcademicDegree;
    let expectedResult: IAcademicDegree | IAcademicDegree[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AcademicDegreeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AcademicDegree(0, 'AAAAAAA', 'AAAAAAA', 0, 0, Degree.ORTA);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AcademicDegree', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AcademicDegree()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AcademicDegree', () => {
        const returnedFromService = Object.assign(
          {
            institutionName: 'BBBBBB',
            discipline: 'BBBBBB',
            startYear: 1,
            endYear: 1,
            obtainedDegree: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AcademicDegree', () => {
        const returnedFromService = Object.assign(
          {
            institutionName: 'BBBBBB',
            discipline: 'BBBBBB',
            startYear: 1,
            endYear: 1,
            obtainedDegree: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AcademicDegree', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
