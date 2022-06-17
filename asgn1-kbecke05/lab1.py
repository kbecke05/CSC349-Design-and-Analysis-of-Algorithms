import sys

# a recursive program to find the singleton elements in a given list

def main():
    filename = sys.argv[1]
    f = open(filename, "r")
    line = f.readline()
    arr = line.strip().split(", ")
    print(singleton(arr, 0, len(arr)-1))

def singleton(arr, min, max):
    if min == max:
        return arr[min]
    else:
        mid = (min+max)//2
        if arr[mid] == arr[mid+1]:
            if len(arr[mid+2:max+1]) % 2 != 0:
                return singleton(arr, mid+2, max)
            else:
                return singleton(arr, min, mid-1)
        if arr[mid] == arr[mid-1]:
            if len(arr[min:mid-1]) % 2 != 0:
                return singleton(arr, min, mid-2)
            else:
                return singleton(arr, mid+1, max)
        else:
            return arr[mid]



if __name__ == "__main__":
    main()