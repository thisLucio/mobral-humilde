class MessageType {
    private _id: number = 0;
    private _description: string = "";
    private _color: string = "";
    private _latitude: number = 0;
    private _longitude: number = 0;
    private _operationType: string = "";

    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get description(): string {
        return this._description;
    }

    set description(value: string) {
        this._description = value;
    }

    get color(): string {
        return this._color;
    }

    set color(value: string) {
        this._color = value;
    }

    get latitude(): number {
        return this._latitude;
    }

    set latitude(value: number) {
        this._latitude = value;
    }

    get longitude(): number {
        return this._longitude;
    }

    set longitude(value: number) {
        this._longitude = value;
    }

    get operationType(): string {
        return this._operationType;
    }

    set operationType(value: string) {
        this._operationType = value;
    }
}

export default MessageType;