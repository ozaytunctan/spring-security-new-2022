import {ServiceResultType} from "../enums/service-result-type.enum";
import {ErrorDto} from "./error-dto.model";


export class ServiceResultDto<T> {

  data: T;
  errors?: ErrorDto[] | any[];
  resultType: ServiceResultType
  isSuccess: boolean = false;

  constructor(data: T,
              resultType: ServiceResultType,
              errors?: ErrorDto[]) {
    this.data = data;
    this.errors = errors;
    this.resultType = resultType;
  }


}
