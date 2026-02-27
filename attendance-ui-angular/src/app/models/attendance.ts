export interface Attendance {
  id?: string;
  employeeId: string;
  locationId: string;
  checkInTime: string;
  checkOutTime?: string;
  status: string;
}
