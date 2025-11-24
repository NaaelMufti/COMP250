public class BuggyQuickSort
{

    public static void quickSort(int[] arr, int left, int right)
    {
        if (left < right)
        {
            int pivotIndex = placeAndDivide(arr, left, right);
            quickSort(arr, left, pivotIndex-1); // pivotIndex-1
            quickSort(arr, pivotIndex + 1, right);
        }
    }

    private static int placeAndDivide(int[] arr, int left, int right) {
        int pivot = arr[right];
        int j = left;
        int i = left;

        while (j < right)
        {
            if (arr[i] < pivot)
            {
                swap(arr, i, j);
                i++;
            }
            j++; // must increment j
        }
        swap(arr, right, j);
        return j;
    }

    private static void swap(int[] arr, int i, int j)  // correct
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args)
    {
        int[] arr = {3, 7, 2, 5, 6, 1, 4};
        quickSort(arr, 0, arr.length - 1);

        System.out.println("Sorted array:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}