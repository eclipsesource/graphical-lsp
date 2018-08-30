import { injectable } from 'inversify'
@injectable()
export class ServiceRegistry {
    private registry: { [id: string]: any; } = {};

    registerService(type: symbol, service: any) {
        this.registry[type.toString()] = service
    }
    getService(type: symbol): any {
        return this.registry[type.toString()]
    }
}