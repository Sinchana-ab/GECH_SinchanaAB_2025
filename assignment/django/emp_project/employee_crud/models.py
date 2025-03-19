from django.db import models

# Create your models here.
class Employee(models.Model):
    name = models.CharField(blank=False, max_length=100)
    email = models.EmailField(blank=False)
    designation = models.CharField(blank=False, max_length=100)
    salary = models.DecimalField(max_digits=10, decimal_places=2, blank=False)

    def __str__(self):
        return self.name