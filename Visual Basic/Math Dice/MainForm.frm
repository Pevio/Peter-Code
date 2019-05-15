VERSION 5.00
Begin VB.Form MainForm 
   BorderStyle     =   1  'Fixed Single
   Caption         =   "Math Dice"
   ClientHeight    =   3825
   ClientLeft      =   3480
   ClientTop       =   2715
   ClientWidth     =   5325
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
   ScaleHeight     =   3825
   ScaleWidth      =   5325
   Begin VB.Timer Timer1 
      Enabled         =   0   'False
      Interval        =   1000
      Left            =   3600
      Top             =   3120
   End
   Begin VB.CommandButton Go 
      Caption         =   "Go!"
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   14.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   495
      Left            =   120
      TabIndex        =   6
      Top             =   3120
      Width           =   1095
   End
   Begin VB.Label CurrentTime 
      Caption         =   "0"
      BeginProperty DataFormat 
         Type            =   1
         Format          =   "0.0"
         HaveTrueFalseNull=   0
         FirstDayOfWeek  =   0
         FirstWeekOfYear =   0
         LCID            =   1033
         SubFormatType   =   1
      EndProperty
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   14.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   495
      Left            =   4080
      TabIndex        =   7
      Top             =   3120
      Width           =   1095
   End
   Begin VB.Line Line2 
      X1              =   120
      X2              =   5160
      Y1              =   2880
      Y2              =   2880
   End
   Begin VB.Line Line1 
      X1              =   120
      X2              =   5160
      Y1              =   1320
      Y2              =   1320
   End
   Begin VB.Label TheNumber 
      Alignment       =   2  'Center
      BackColor       =   &H000000C0&
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   36
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   855
      Index           =   4
      Left            =   4080
      TabIndex        =   5
      Top             =   1680
      Width           =   1095
   End
   Begin VB.Label TheNumber 
      Alignment       =   2  'Center
      BackColor       =   &H000000C0&
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   36
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   855
      Index           =   3
      Left            =   2760
      TabIndex        =   4
      Top             =   1680
      Width           =   1095
   End
   Begin VB.Label TheNumber 
      Alignment       =   2  'Center
      BackColor       =   &H000000C0&
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   36
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   855
      Index           =   2
      Left            =   1440
      TabIndex        =   3
      Top             =   1680
      Width           =   1095
   End
   Begin VB.Label TheNumber 
      Alignment       =   2  'Center
      BackColor       =   &H000000C0&
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   36
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   855
      Index           =   1
      Left            =   120
      TabIndex        =   2
      Top             =   1680
      Width           =   1095
   End
   Begin VB.Label Digit2 
      Alignment       =   2  'Center
      BackColor       =   &H0000FF00&
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   36
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   855
      Left            =   2760
      TabIndex        =   1
      Top             =   120
      Width           =   1095
   End
   Begin VB.Label Digit1 
      Alignment       =   2  'Center
      BackColor       =   &H0000FF00&
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   36
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   855
      Left            =   1440
      TabIndex        =   0
      Top             =   120
      Width           =   1095
   End
End
Attribute VB_Name = "MainForm"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Dim xxx As Long, yyy As Long, zzz As Long, AAA As Long, BBB As Long, CCC As Long, DDD As Long
Dim Target As Integer
Dim NumberUsing
Dim InputNumbers(1 To 5) As Long
Dim Solution As Boolean
Dim TheSolution As String

Private Sub CurrentTime_Click()

If CurrentTime.Caption <> 0 And Solution = True Then MsgBox "The Solution:" & vbCrLf & TheSolution, vbOKOnly, "Math Dice"

End Sub

Private Sub Go_Click()

Randomize

Digit1.Caption = Int(Rnd(200) * 10)
Digit2.Caption = Int(Rnd(200) * 10)
TheNumber(1).Caption = Int(Rnd(200) * 10)
TheNumber(2).Caption = Int(Rnd(200) * 10)
TheNumber(3).Caption = Int(Rnd(200) * 10)
TheNumber(4).Caption = Int(Rnd(200) * 10)
DoEvents
For xxx = 1 To 4
    InputNumbers(xxx) = TheNumber(xxx).Caption
Next xxx

CurrentTime.Caption = 0
Timer1.Enabled = True
Target = (Digit1 * 10) + Digit2
DoEvents
Solution = False
TheSolution = ""

For xxx = 1 To 4
    For yyy = 1 To 4
        For zzz = 1 To 4
            For AAA = 1 To 4
                If xxx <> yyy And xxx <> zzz And xxx <> AAA And yyy <> zzz And yyy <> AAA And zzz <> AAA Then
                    'We are now talking about the order of the numbers in the equation.  The previous line
                    'makes sure that each number is in a different posistion - the first number can't be in
                    'the first and third places.
                    For BBB = 1 To 5
                        For CCC = 1 To 5
                            For DDD = 1 To 5
                                'We are now talking about the operations used in the equation.  This time,
                                'there is repitition, so 5 is used each time.  There are 5 operations
                                'allowed: +, -, *, /, ^
                                
                                NumberUsing = Operation(DDD, Operation(CCC, Operation(BBB, InputNumbers(xxx), InputNumbers(yyy)), InputNumbers(zzz)), InputNumbers(AAA))
                                If NumberUsing = Target Then
                                    If Solution = False Then TheSolution = InputNumbers(xxx) & " " & FindOperation(BBB) & " " & InputNumbers(yyy) & " " & FindOperation(CCC) & " " & InputNumbers(zzz) & " " & FindOperation(DDD) & " " & InputNumbers(AAA)
                                    Solution = True
                                End If
                                
                                NumberUsing = Operation(CCC, Operation(BBB, InputNumbers(xxx), InputNumbers(yyy)), Operation(DDD, InputNumbers(zzz), InputNumbers(AAA)))
                                If NumberUsing = Target Then
                                    If Solution = False Then TheSolution = InputNumbers(xxx) & " " & FindOperation(BBB) & " " & InputNumbers(yyy) & " " & FindOperation(CCC) & " " & "(" & InputNumbers(zzz) & " " & FindOperation(DDD) & " " & InputNumbers(AAA) & ")"
                                    Solution = True
                                End If
                                
                                NumberUsing = Operation(DDD, Operation(BBB, InputNumbers(xxx), Operation(CCC, InputNumbers(yyy), InputNumbers(zzz))), InputNumbers(AAA))
                                If NumberUsing = Target Then
                                    If Solution = False Then TheSolution = InputNumbers(xxx) & " " & FindOperation(BBB) & " " & "(" & InputNumbers(yyy) & " " & FindOperation(CCC) & " " & InputNumbers(zzz) & ") " & FindOperation(DDD) & " " & InputNumbers(AAA)
                                    Solution = True
                                End If
                                
                                NumberUsing = Operation(BBB, InputNumbers(xxx), Operation(DDD, Operation(CCC, InputNumbers(yyy), InputNumbers(zzz)), InputNumbers(AAA)))
                                If NumberUsing = Target Then
                                    If Solution = False Then TheSolution = InputNumbers(xxx) & " " & FindOperation(BBB) & " " & "(" & InputNumbers(yyy) & " " & FindOperation(CCC) & " " & InputNumbers(zzz) & " " & FindOperation(DDD) & " " & InputNumbers(AAA) & ")"
                                    Solution = True
                                End If
                                DoEvents
                            Next DDD
                        Next CCC
                    Next BBB
                End If
            Next AAA
        Next zzz
    Next yyy
Next xxx

End Sub

Private Sub Timer1_Timer()

CurrentTime.Caption = CurrentTime.Caption + 1

End Sub

Function Operation(ByVal OperationNumber As Integer, ByVal I1, ByVal I2)

On Error GoTo TooBig:
If OperationNumber = 1 Then Operation = I1 + I2
If OperationNumber = 2 Then Operation = I1 - I2
If OperationNumber = 3 Then Operation = I1 * I2
If OperationNumber = 4 Then Operation = I1 / I2
If OperationNumber = 5 Then Operation = I1 ^ I2

Exit Function
TooBig:
Operation = 0

End Function

Function FindOperation(ByVal OperationNumber As Integer) As String

If OperationNumber = 1 Then FindOperation = "+"
If OperationNumber = 2 Then FindOperation = "-"
If OperationNumber = 3 Then FindOperation = "x"
If OperationNumber = 4 Then FindOperation = "/"
If OperationNumber = 5 Then FindOperation = "^"

End Function
