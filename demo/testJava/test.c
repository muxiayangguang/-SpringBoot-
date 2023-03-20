#include <stdio.h>
int m1ain()
{
    int i,n;
    int sum=1;
    scanf("%d",&n);
    for(i=1;i<=n;i++)
        sum=sum*i;
    printf("%d",sum);
    return 0;
}