import dayjs from "dayjs";

const FULL_DATE_FORMAT = "MMM D, YYYY";

export function formatDate(dateString: string) {
  const date = dayjs(dateString);

  if (!date.isValid()) return "Invalid Date";

  const now = dayjs();
  const hours = now.diff(date, "hour");
  const days = now.diff(date, "day");

  if (hours < 1) return "Just now";
  if (hours < 24) return `${hours} hour${hours > 1 ? "s" : ""} ago`;
  if (days < 7) return `${days} day${days > 1 ? "s" : ""} ago`;
  return date.format(FULL_DATE_FORMAT);
}

export function formatFullDate(dateString: string) {
  const date = dayjs(dateString);

  if (!date.isValid()) return "Invalid Date";

  return date.format(FULL_DATE_FORMAT);
}
