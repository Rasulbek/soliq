import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAcademicDegree } from 'app/shared/model/academic-degree.model';

type EntityResponseType = HttpResponse<IAcademicDegree>;
type EntityArrayResponseType = HttpResponse<IAcademicDegree[]>;

@Injectable({ providedIn: 'root' })
export class AcademicDegreeService {
  public resourceUrl = SERVER_API_URL + 'api/academic-degrees';

  constructor(protected http: HttpClient) {}

  create(academicDegree: IAcademicDegree): Observable<EntityResponseType> {
    return this.http.post<IAcademicDegree>(this.resourceUrl, academicDegree, { observe: 'response' });
  }

  update(academicDegree: IAcademicDegree): Observable<EntityResponseType> {
    return this.http.put<IAcademicDegree>(this.resourceUrl, academicDegree, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAcademicDegree>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAcademicDegree[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
