import { EntityType } from "./entityType";

export type Contact = {
  id: number;
  name: string;
  entityType: EntityType;
  chatUsername: string;
};