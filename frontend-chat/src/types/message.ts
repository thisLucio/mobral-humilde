export type Message = {
  userId: number;
  equipmentId: number;
  content: string;
  timestamp: Date;
  sender: string;
  to: string;
  isFromSender: boolean;
};
