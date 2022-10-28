type GenericObject = { [key: string]: any };

export function groupBy<T>(
  array: Array<T>,
  property: (p: T) => any
): GenericObject {
  return array.reduce((acc: Array<Array<T>>, current: T) => {
    if (!acc[property(current)]) {
      acc[property(current)] = [];
    }
    acc[property(current)].push(current);
    return acc;
  }, {} as Array<Array<T>>);
}
