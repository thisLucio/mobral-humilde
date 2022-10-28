import { EntityType } from "./entityType";

export type ChatUser = {
  id: number;
  name: string;
  photo?: string;
  entityType: EntityType;
  chatUsername: string;
};
