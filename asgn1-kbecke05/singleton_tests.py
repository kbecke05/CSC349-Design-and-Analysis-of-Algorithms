import unittest
import lab1


# Testing for algorithm correctness
class TestSingleton(unittest.TestCase):
    def test1(self):
        num = [-2, -2, 5, 5, 12, 12, 67, 67, 72, 80, 80]
        self.assertEqual(lab1.singleton(num), 72)

    def test2(self):
        num = [0, 0, 2, 2, 4, 4, 6, 6, 8, 8, 10, 12, 12, 14, 14]
        self.assertEqual(lab1.singleton(num), 10)

    def test3(self):
        num = [1247389491]
        self.assertEqual(lab1.singleton(num), 1247389491)

    def test4(self):
        num = [22, 22, 43, 43, 68, 79, 79]
        self.assertEqual(lab1.singleton(num), 68)

    def test5(self):
        num = [11, 12, 12, 14, 14, 89, 89, 100, 100]
        self.assertEqual(lab1.singleton(num), 11)

    def test6(self):
        num = [11, 11, 12, 14, 14, 89, 89, 100, 100]
        self.assertEqual(lab1.singleton(num), 12)

    def test7(self):
        num = [-11, -11, -12, -14, -14, -89, -89, -100, -100]
        self.assertEqual(lab1.singleton(num), -12)

    def test8(self):
        num = [13, 22, 22, 43, 43, 68, 68, 79, 79]
        self.assertEqual(lab1.singleton(num), 13)

    def test9(self):
        num = [13, 13, 22, 22, 43, 43, 68, 68, 79, 79]
        self.assertEqual(lab1.singleton(num), 43)
        
    

