VERSION 5.00
Begin VB.Form MainForm 
   BorderStyle     =   1  'Fixed Single
   Caption         =   "Prime Number"
   ClientHeight    =   3045
   ClientLeft      =   7215
   ClientTop       =   4200
   ClientWidth     =   4050
   BeginProperty Font 
      Name            =   "Times New Roman"
      Size            =   12
      Charset         =   0
      Weight          =   400
      Underline       =   0   'False
      Italic          =   0   'False
      Strikethrough   =   0   'False
   EndProperty
   Icon            =   "MainForm.frx":0000
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   3045
   ScaleWidth      =   4050
   Begin VB.CommandButton cmdFactor 
      Caption         =   "Factorization"
      Default         =   -1  'True
      Height          =   375
      Left            =   2400
      TabIndex        =   4
      Top             =   1920
      Width           =   1455
   End
   Begin VB.CommandButton cmdFigure 
      Caption         =   "Prime?"
      Height          =   375
      Left            =   2400
      TabIndex        =   3
      Top             =   1200
      Width           =   1455
   End
   Begin VB.ListBox BadNumbers 
      Height          =   2340
      ItemData        =   "MainForm.frx":030A
      Left            =   120
      List            =   "MainForm.frx":030C
      TabIndex        =   2
      Top             =   600
      Width           =   2175
   End
   Begin VB.TextBox txtNumber 
      Height          =   375
      Left            =   1320
      TabIndex        =   1
      Top             =   120
      Width           =   2655
   End
   Begin VB.Label Label1 
      Alignment       =   1  'Right Justify
      BackStyle       =   0  'Transparent
      Caption         =   "Number:  "
      Height          =   375
      Left            =   120
      TabIndex        =   0
      Top             =   120
      Width           =   1215
   End
End
Attribute VB_Name = "MainForm"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim xxx
Dim Primes(1 To 2000000) As Long
Dim NumPrimes As Long

Private Sub cmdFactor_Click()

On Error GoTo asdfasdf:
BadNumbers.Clear
MainForm.MousePointer = vbHourglass
cmdFigure.Enabled = False
cmdFactor.Enabled = False
Dim Current
Current = txtNumber.Text
DoEvents

If txtNumber.Text = Int(txtNumber.Text) And Int(txtNumber.Text) > 1 Then
DoAgain:
    For xxx = 1 To NumPrimes
        If Int(Current) = Int(Primes(xxx)) Then
            BadNumbers.AddItem Primes(xxx)
            MainForm.MousePointer = 0
            cmdFigure.Enabled = True
            cmdFactor.Enabled = True
            Exit Sub
        End If
        If Current / Primes(xxx) = Int(Current / Primes(xxx)) Then
            BadNumbers.AddItem Primes(xxx)
            DoEvents
            Current = Current / Primes(xxx)
            GoTo DoAgain:
        End If
    Next xxx
    If Primes(NumPrimes) > Int(Sqr(Current) + 1) Then
        BadNumbers.AddItem Current
        MainForm.MousePointer = 0
        cmdFigure.Enabled = True
        cmdFactor.Enabled = True
        Exit Sub
    Else
        For xxx = Primes(NumPrimes) To Int(Sqr(Current) + 1)
            If Current / xxx = Int(Current / xxx) Then
                BadNumbers.AddItem xxx
                DoEvents
                Current = Current / xxx
                GoTo DoAgain:
            End If
        Next xxx
    End If
Else
    MsgBox "You must enter in an integer greater than 1.", vbExclamation, "Prime Number"
    MainForm.MousePointer = 0
    cmdFigure.Enabled = True
    cmdFactor.Enabled = True
    Exit Sub
End If

asdfasdf:
MainForm.MousePointer = 0
MsgBox "There was a problem exicuting the task.  It may be because the number is too large.", vbExclamation, "Prime Number"
cmdFigure.Enabled = True
cmdFactor.Enabled = True

End Sub

Private Sub cmdFigure_Click()

On Error GoTo asdfasdf:
BadNumbers.Clear
MainForm.MousePointer = vbHourglass
cmdFigure.Enabled = False
cmdFactor.Enabled = False
DoEvents

If txtNumber.Text = Int(txtNumber.Text) And Int(txtNumber.Text) > 1 Then
    For xxx = 1 To NumPrimes
        If (Primes(xxx) ^ 2) > txtNumber.Text Then xxx = NumPrimes
        If txtNumber.Text / Primes(xxx) = Int(txtNumber.Text / Primes(xxx)) Then
            BadNumbers.AddItem Primes(xxx)
            MainForm.MousePointer = 0
            cmdFigure.Enabled = True
            cmdFactor.Enabled = True
            Exit Sub
        End If
    Next xxx
    If txtNumber.Text > (Primes(NumPrimes) ^ 2) Then
        For xxx = (Primes(NumPrimes) ^ 2) To Int(Sqr(txtNumber.Text) + 1)
            If txtNumber.Text / Primes(xxx) = Int(txtNumber.Text / Primes(xxx)) Then
                BadNumbers.AddItem xxx
                MainForm.MousePointer = 0
                cmdFigure.Enabled = True
                cmdFactor.Enabled = True
            End If
        Next xxx
    End If
Else
    MsgBox "You must enter in an integer greater than 1.", vbExclamation, "Prime Number"
    MainForm.MousePointer = 0
    cmdFigure.Enabled = True
    cmdFactor.Enabled = True
    Exit Sub
End If

MainForm.MousePointer = 0
cmdFigure.Enabled = True
cmdFactor.Enabled = True

Exit Sub

asdfasdf:
MainForm.MousePointer = 0
MsgBox "There was a problem exicuting the task.  It may be because the number is too large.", vbExclamation, "Prime Number"
cmdFigure.Enabled = True
cmdFactor.Enabled = True

End Sub

Private Sub Form_Load()

On Error GoTo done:

Open "Primes.txt" For Input As #1
    a = 0
    NumPrimes = 1
    Do While a = 0
        Input #1, Primes(NumPrimes)
        NumPrimes = NumPrimes + 1
    Loop
done:
NumPrimes = NumPrimes - 1
Close #1

End Sub
