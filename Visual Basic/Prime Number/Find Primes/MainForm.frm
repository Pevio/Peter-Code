VERSION 5.00
Begin VB.Form MainForm 
   Caption         =   "Form1"
   ClientHeight    =   3015
   ClientLeft      =   120
   ClientTop       =   465
   ClientWidth     =   4560
   LinkTopic       =   "Form1"
   ScaleHeight     =   3015
   ScaleWidth      =   4560
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton Command1 
      Caption         =   "Command1"
      Height          =   1335
      Left            =   840
      TabIndex        =   0
      Top             =   1200
      Width           =   2415
   End
   Begin VB.Label Label1 
      Caption         =   "Label1"
      Height          =   615
      Left            =   960
      TabIndex        =   1
      Top             =   360
      Width           =   1935
   End
End
Attribute VB_Name = "MainForm"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim List As String
Dim xxx As Long, yyy As Long
Dim KnownPrimes(1 To 1000000) As Long
Dim PrimeCount As Long

Private Sub Command1_Click()

PrimeCount = 1
KnownPrimes(1) = 2
List = "2,"
For xxx = 2 To 2500000
    xxx = xxx + 1
    For yyy = 1 To PrimeCount
        If xxx / KnownPrimes(yyy) = Int(xxx / KnownPrimes(yyy)) Then
            yyy = xxx
            GoTo Skip:
        End If
        If KnownPrimes(yyy) > Sqr(xxx) Then
            List = List & xxx & ","
            PrimeCount = PrimeCount + 1
            KnownPrimes(PrimeCount) = xxx
            yyy = xxx
        End If
Skip:
    Next yyy
    
    If (xxx + 1) / 100000 = Int((xxx / 100000) + 1) Then
        Label1.Caption = xxx + 1
        DoEvents
    End If
    If xxx > 2500000 Then GoTo done:
Next xxx
done:

Open "Primes.txt" For Output As #1
    Print #1, List
Close #1

End Sub
